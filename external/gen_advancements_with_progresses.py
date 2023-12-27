import os
import demjson

REQUIREMENTS='requirements'
DISPLAY='display'
CRITERIA='criteria'
UTF8='utf-8'

folder_path = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'BACAP')

found_json_files = []

for root, dirs, files in os.walk(folder_path):
    if 'advancements' in root and 'bacap' not in root and 'statistic' not in root:
        for file in files:
            if file.endswith('.json'):
                relative_path = os.path.relpath(os.path.join(root, file), folder_path)
                with open(os.path.join(root, file), 'r', encoding=UTF8) as json_file:
                    json_data = json_file.read()
                    try:
                        data = demjson.decode(json_data)
                        if len(data.get(CRITERIA, {})) > 1 and (not data.get(REQUIREMENTS) or len(data.get(REQUIREMENTS, [])) > 1) and not data.get(DISPLAY, {}).get('hidden') and DISPLAY in data:
                            criteria = []
                            if data.get(REQUIREMENTS):
                                for requirement in data[REQUIREMENTS]:
                                    criteria.append(requirement[0])
                            else:
                                criteria = list(data[CRITERIA].keys())
                            found_json_files.append((relative_path, criteria))
                    except demjson.JSONDecodeError as e:
                        print(f"Could not parse {relative_path}: {e}")
                        continue

found_json_files.sort()
output_file_path = os.path.join(folder_path, 'advancements_with_progresses.txt')
with open(output_file_path, 'w', encoding=UTF8) as output_file:
    for i, (file, criteria) in enumerate(found_json_files):
        parts = file.split(os.sep)
        new_path = parts[-4] + ':' + parts[-2] + '/' + parts[-1].replace('.json', '')
        criteria_str = ', '.join(criteria)

        if i != len(found_json_files) - 1:
            output_file.write(f"{new_path} [{criteria_str}]\n")
        else:
            output_file.write(f"{new_path} [{criteria_str}]")
