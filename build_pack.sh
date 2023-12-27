#!/bin/bash

readonly NAME_COLOR_FROM="#07c5fa"
readonly NAME_COLOR_TO="#dff2f7"

readonly VERSION_COLOR_MAJOR="#fd99b1"
readonly VERSION_COLOR_MINOR="#fee6a8"
readonly VERSION_COLOR_PATCH="#e4adf7"

readonly modId=$1
readonly packVersion=$2
readonly packName=$3
readonly author=$4
readonly repoUrl=$5
readonly minecraftVersion=$6
readonly loaderVersion=$7
readonly isDebug=$8

readonly packDir="pack"

rm -rf "build/libs"
rm "$packDir/mods/$modId"*
rm -rf "src/main/generated"
./gradlew runDatagen
./gradlew build -PisDebug="$isDebug"
cp "build/libs/$modId"* "$packDir/mods"

cd "$packDir" || exit

wordCenter="$packName"
wordStartArray=()
wordEndArray=()
while [ -n "$wordCenter" ]; do
    wordCenterLen=${#wordCenter}
    if [ "$wordCenterLen" -eq 1 ] || [ "$wordCenterLen" -eq 3 ] || [ "$wordCenterLen" -eq 4 ]; then
        piece=1
    else
        piece=2
    fi
    if [ "$wordCenterLen" -eq $piece ]; then
        break
    fi
    wordStartArray+=("${wordCenter:0:$piece}")
    wordEndArray+=("${wordCenter: -$piece}")
    wordCenter="${wordCenter:$piece:-$piece}"
done

nameColorSteps=${#wordStartArray[@]}

nameColors=()
from_r=$(printf "%d" 0x"${NAME_COLOR_FROM:1:2}")
from_g=$(printf "%d" 0x"${NAME_COLOR_FROM:3:2}")
from_b=$(printf "%d" 0x"${NAME_COLOR_FROM:5:2}")
to_r=$(printf "%d" 0x"${NAME_COLOR_TO:1:2}")
to_g=$(printf "%d" 0x"${NAME_COLOR_TO:3:2}")
to_b=$(printf "%d" 0x"${NAME_COLOR_TO:5:2}")
for ((i = 0; i <= nameColorSteps; i++)); do
    r=$((from_r + (to_r - from_r) * i / nameColorSteps))
    g=$((from_g + (to_g - from_g) * i / nameColorSteps))
    b=$((from_b + (to_b - from_b) * i / nameColorSteps))
    hex=$(printf "#%02x%02x%02x" "$r" "$g" "$b")
    nameColors+=("$hex")
done

coloredPackName=""
for ((i=0; i<${#wordStartArray[@]}; i++)); do
    colorIndex=$((i % (${#nameColors[@]} - 1)))
    coloredPackName+="&{${nameColors[$colorIndex]}}${wordStartArray[$i]}"
done

coloredPackName+="&{${nameColors[-1]}}${wordCenter}"

for ((i=${#wordEndArray[@]} - 1; i>=0; i--)); do
    colorIndex=$((i % (${#nameColors[@]} - 1)))
    coloredPackName+="&{${nameColors[$colorIndex]}}${wordEndArray[$i]}"
done

IFS='.' read -r major minor patch <<< "$packVersion"
coloredPackVersion=""
if [[ -n "$major" ]]; then
    coloredPackVersion+="&{${VERSION_COLOR_MAJOR}}$major"
fi
if [[ -n "$minor" ]]; then
    coloredPackVersion+=".&{${VERSION_COLOR_MINOR}}$minor"
fi
if [[ -n "$patch" ]]; then
    coloredPackVersion+=".&{${VERSION_COLOR_PATCH}}$patch"
fi

packMetadataPath="pack.toml"
packMetadata=$(<"$packMetadataPath")
sed -i "s/\${packName}/$(printf '%s\n' "${packName}" | sed -e 's/[\/&]/\\&/g')/g" "${packMetadataPath}"
sed -i "s/\${author}/$(printf '%s\n' "${author}" | sed -e 's/[\/&]/\\&/g')/g" "${packMetadataPath}"
sed -i "s/\${packVersion}/$(printf '%s\n' "${packVersion}" | sed -e 's/[\/&]/\\&/g')/g" "${packMetadataPath}"
sed -i "s/\${loaderVersion}/$(printf '%s\n' "${loaderVersion}" | sed -e 's/[\/&]/\\&/g')/g" "${packMetadataPath}"
sed -i "s/\${minecraftVersion}/$(printf '%s\n' "${minecraftVersion}" | sed -e 's/[\/&]/\\&/g')/g" "${packMetadataPath}"

customHudProfilePath="config/custom-hud/profile1.txt"
customHudProfile=$(<"$customHudProfilePath")
sed -i "s/\${coloredPackName}/$(printf '%s\n' "${coloredPackName}" | sed -e 's/[\/&]/\\&/g')/g" "${customHudProfilePath}"
sed -i "s/\${coloredPackVersion}/$(printf '%s\n' "${coloredPackVersion}" | sed -e 's/[\/&]/\\&/g')/g" "${customHudProfilePath}"

mainMenuCreditsConfigPath="config/isxander-main-menu-credits.json"
mainMenuCreditsConfig=$(<"$mainMenuCreditsConfigPath")
sed -i "s/\${packName}/$(printf '%s\n' "${packName}" | sed -e 's/[\/&]/\\&/g')/g" "${mainMenuCreditsConfigPath}"
sed -i "s/\${packVersion}/$(printf '%s\n' "${packVersion}" | sed -e 's/[\/&]/\\&/g')/g" "${mainMenuCreditsConfigPath}"
sed -i "s/\${repoUrl}/$(printf '%s\n' "${repoUrl}" | sed -e 's/[\/&]/\\&/g')/g" "${mainMenuCreditsConfigPath}"

packwiz refresh
packwiz modrinth export

echo "$packMetadata" > "$packMetadataPath"
echo "$customHudProfile" > "$customHudProfilePath"
echo "$mainMenuCreditsConfig" > "$mainMenuCreditsConfigPath"
