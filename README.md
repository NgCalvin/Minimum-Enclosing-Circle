# README


## Compile

```
$ javac -cp . MEC/*.java
```

This will compile all the java files needed.
### Usage

```
$ java -cp . MEC/MEC [Dataset]
```

- Arguments:
  - `Dataset`: (required) the dataset file to process
 
## Gnuplot command

```
$ set size ratio -1
$ set grid
$ set xrange[Lower:Upper]
$ set yrange[Lower:Upper]
$ set object 1 circle at [xCoord of center],[yCoord of center] size first [radius]
$ plot '[File to plot]' using 1:2 title ""
```

These are the specific set of command for Gnuplot to plot the correct graph.
