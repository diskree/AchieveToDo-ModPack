import os
import re

base_path = os.path.dirname(os.path.realpath(__file__))
translation_file_path = os.path.join(base_path, 'BACAP/base_language_file.json')
bacap_path = os.path.join(base_path, 'BACAP')

with open(translation_file_path, 'r', encoding='utf-8') as f:
    translations = f.read()
    translated_texts = set(re.findall(r'"(.*?)"\s*:', translations))

ignore_pattern = re.compile(r"^\w+(\.\w+)+$")

untranslated_texts = []

subfolders = [d for d in os.listdir(bacap_path) if os.path.isdir(os.path.join(bacap_path, d))]

total_files = sum([len(files) for dirpath, dirnames, files in os.walk(bacap_path)])
files_processed = 0
total_texts_found = 0

for subfolder in subfolders:
    for dirpath, dirnames, filenames in os.walk(os.path.join(bacap_path, subfolder)):
        for filename in filenames:
            file_extension = os.path.splitext(filename)[1]
            if file_extension in ['.json', '.mcfunction']:
                with open(os.path.join(dirpath, filename), 'r', encoding='utf-8') as f:
                    content = f.read()
                    texts = re.findall(r'"translate":"(.*?)"', content)
                    total_texts_found += len(texts)
                    for text in texts:
                        if text not in translated_texts and not ignore_pattern.match(text):
                            relative_path = os.path.relpath(os.path.join(dirpath, filename), bacap_path)
                            untranslated_texts.append((text, relative_path))
            files_processed += 1
            print(f"\rLoading: {int((files_processed / total_files) * 100)}%", end="")

print("\rLoading: OK!")
if untranslated_texts:
    print(f"\nMissing strings in base_language_file.json from datapacks ({len(untranslated_texts)}):")
    for text, path in untranslated_texts:
        print(f'"{text}" in "{path}"')
else:
    print("\nAll strings from datapacks are present in the base_language_file.json")
