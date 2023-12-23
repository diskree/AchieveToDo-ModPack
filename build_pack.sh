#!/bin/bash

readonly MOD_LOADER="fabric"

readonly modId=$1
readonly packVersion=$2
readonly packName=$3
readonly author=$4
readonly repoUrl=$5
readonly minecraftVersion=$6
readonly loaderVersion=$7

readonly packDir="pack"

rm -f -r "build/libs"
rm "$packDir/mods/$modId"*
./gradlew build -PisDebug=false
cp "build/libs/$modId"* "$packDir/mods"

cd "$packDir" || exit

packwiz init -r \
    --name "$packName" \
    --author "$author" \
    --version "$packVersion" \
    --mc-version "$minecraftVersion" \
    --modloader "$MOD_LOADER" \
    --fabric-version "$loaderVersion"

mainMenuCreditsConfigFile="config/isxander-main-menu-credits.json"
for menu in "main_menu" "pause_menu"; do
    jq --arg packName "$packName" --arg packVersion "$packVersion" --arg repoUrl "$repoUrl" '
        .'"$menu"'.bottom_left[0] |= (. + {
            "text": ($packName + " " + $packVersion),
            "clickEvent": (.clickEvent + { "value": $repoUrl })
        })
    ' "$mainMenuCreditsConfigFile" > tmpfile && mv tmpfile "$mainMenuCreditsConfigFile"
done

packwiz refresh
packwiz modrinth export
