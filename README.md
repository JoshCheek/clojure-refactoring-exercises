# refactoring-exercises

Josh Cheek's solutions :)

I'm running these like:

```sh
$ lein spec spec/refactoring_exercises/video_store/video_store_spec.clj -a | ruby -ne '~/^---/ ? print("\e[H\e[2J#{n+=1}") : ~/^(took|reloading|  \/Users)/ ? nil : print; BEGIN { n = 0 }'
```
