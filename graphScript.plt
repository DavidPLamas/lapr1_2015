reset
set style fill transparent solid 0.4
set terminal png
set output 'graphtest.png'
set multiplot
set title 'GraphTest'
set xrange[] writeback
set yrange[] writeback
set trange[] writeback

f0(x) = -0.67*x +0.00
set label 1 '     (3.00 ; 2.00)' at 3.00,2.00 point ps 2 pointtype 2

plot f0(x) lt rgb 'black' title '0 = 2X1 + 3x2',NaN lt rgb 'gold' title '2x1 <= 3'

set parametric
set xrange restore
set yrange restore
set trange [-500:500]
plot 1.50,t lt rgb 'gold' notitle

unset parametric
unset multiplot
unset output
unset terminal
