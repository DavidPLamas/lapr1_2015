reset
set style fill transparent solid 0.4
set terminal png
set output 'graph.png'
set title 'Graph'

 r0(x) = -0.60*x +7.20
 r2(x) = 6.00
 r3(x) = -1.50*x +9.00
set label 1 '     (2.00 ; 6.00)' at 2.00,6.00 point ps 2 pointtype 2
plot r0(x) lt rgb 'black' title '-0.60*x +7.20',r2(x) lt rgb 'blue' title '6.00',r3(x) lt rgb 'orange' title '-1.50*x +9.00'