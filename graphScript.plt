reset
set style fill transparent solid 0.4
set terminal png
set output 'graph.png'
set multiplot
set title 'Graph'
set xrange[] writeback
set yrange[] writeback
set trange[] writeback

f0(x) = -0.60*x +7.20
f2(x) = 6.00
f3(x) = -1.50*x +9.00
f4(x) = -0.60*x +0.00
f5(x) = -0.60*x +6.00
set label 1 '     (2.00 ; 6.00)' at 2.00,6.00 point ps 2 pointtype 2

plot f0(x) lt rgb 'black' title 'y = -0.60*x +7.20',\
NaN lt rgb 'blue' title 'x = 4.00',\
f2(x) lt rgb 'orange' title 'y = 6.00',\
f3(x) lt rgb 'yellow' title 'y = -1.50*x +9.00',\
f4(x) lt rgb 'red' title 'y = -0.60*x +0.00',\
f5(x) lt rgb 'pink' title 'y = -0.60*x +6.00'

set parametric
set xrange restore
set yrange restore
set trange [-500:500]
plot 4.00,t lt rgb 'blue' notitle

unset parametric
unset multiplot
unset output
unset terminal
