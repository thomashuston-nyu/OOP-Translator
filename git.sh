#!/bin/sh

git filter-branch -f --env-filter '

an="$GIT_AUTHOR_NAME"
am="$GIT_AUTHOR_EMAIL"
cn="$GIT_COMMITTER_NAME"
cm="$GIT_COMMITTER_EMAIL"

if [ "$GIT_COMMITTER_EMAIL" = "tph227@nyu.edu" ]
then
    cn="thomashuston"
        cm="twelveangrymen435@gmail.com"
        fi
        if [ "$GIT_AUTHOR_EMAIL" = "tph227@nyu.edu" ]
        then
            an="thomashuston"
                am="twelveangrymen435@gmail.com"
                fi
                
                export GIT_AUTHOR_NAME="$an"
                export GIT_AUTHOR_EMAIL="$am"
                export GIT_COMMITTER_NAME="$cn"
                export GIT_COMMITTER_EMAIL="$cm"
                '
