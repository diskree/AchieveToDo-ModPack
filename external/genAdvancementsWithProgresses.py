import os
import argparse
import json

def validate_folder(path):
    if not os.path.isdir(path):
        raise argparse.ArgumentTypeError(f"{path} is not a valid path")
    return path

parser = argparse.ArgumentParser(description='Process folder path.')
parser.add_argument('path', type=validate_folder, help='The path to the directory')

args = parser.parse_args()

folder_path = args.path

found_json_files = []

for root, dirs, files in os.walk(folder_path):
    if 'advancements' in root and 'bacap' not in root and 'statistic' not in root:
        for file in files:
            if file.endswith('.json'):
                relative_path = os.path.relpath(os.path.join(root, file), folder_path)
                with open(os.path.join(root, file), 'r', encoding='utf-8') as json_file:
                    try:
                        data = json.load(json_file)
                        if len(data.get('criteria', {})) > 1 and (not data.get('requirements') or len(data.get('requirements', [])) > 1) and not data.get('display', {}).get('hidden') == 'true' and 'display' in data:
                            criteria = []
                            if data.get('requirements'):
                                for requirement in data['requirements']:
                                    criteria.append(requirement[0])
                            else:
                                criteria = list(data['criteria'].keys())
                            found_json_files.append((relative_path, criteria))
                    except json.JSONDecodeError:
                        print(f"Could not parse {relative_path}")

found_json_files.sort()
output_file_path = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'advancements_with_progresses.txt')
with open(output_file_path, 'w', encoding='utf-8') as output_file:
    for i, (file, criteria) in enumerate(found_json_files):
        parts = file.split(os.sep)
        new_path = parts[-4] + ':' + parts[-2] + '/' + parts[-1].replace('.json', '')
        criteria_str = ', '.join(criteria)

        if i != len(found_json_files) - 1:
            output_file.write(f"{new_path} [{criteria_str}]\n")
        else:
            output_file.write(f"{new_path} [{criteria_str}]")