# kutils

## Overview

License: GNU General Public License v3.0

Github: https://github.com/digorydoo/kutils

kutils are a bunch of general Kotlin utils, CJK utils, Math utils.

## Branching

Branch `main` is the main branch and published to GitHub. Work should be squashed before committing to `main`. Here's a
quick recipe for maintainers:

```
$ git checkout feature        # check out your feature branch and start working
$ git commit -a               # repeatedly commit your changes
$ git checkout main           # check out github's main branch
$ git pull                    # make sure you're up-to-date
$ git merge --squash feature  # squashed commit, resolve any conflicts
$ git push                    # upload to github
$ git branch -D feature       # you CANNOT rebase, or you'll get conflicts!
$ git checkout -b feature     # create a new feature branch
```
