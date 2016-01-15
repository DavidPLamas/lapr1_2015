reset
set style fill transparent solid 0.4
set terminal png
set output 'graph.png'
set multiplot
set title 'Graph'
set xrange[] writeback
set yrange[] writeback
set trange[] writeback

f0(x) = -0.50*x +2.00
f1(x) = 0.33*x -0.33
f2(x) = 0.50*x +2.00
f3(x) = -0.50*x +0.00
set label 1 '     (0.00 ; 2.00)' at 0.00,2.00 point ps 2 pointtype 2

plot f0(x) lt rgb 'black' title 'y = -0.50*x +2.00',\
f1(x) lt rgb 'gold' title 'y = 0.33*x -0.33',\
f2(x) lt rgb 'dark-orange' title 'y = 0.50*x +2.00',\
f3(x) lt rgb 'grey' title 'y = -0.50*x +0.00'

set parametric
set xrange restore
set yrange restore
set trange [-500:500]
p

unset parametric
unset multiplot
unset output
unset terminal
