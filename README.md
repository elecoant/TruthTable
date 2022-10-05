# TruthTable

## Overview

TruthTable lets you input a propositional formula into a command line interface and generates its truth table in PDF format.

### operators
- not
- and
- or
- xor
- implies
- equals

### constants
- true
- false

### variable naming
- only letters

## Syntax

```
<formula_name> : <formula> ;
```
  
## Examples

```
f: (not (a and b) equals (not a or not b));
```
```
g: ((x and (y or z)) equals ((x and y) or (x and z)));
```
```
h: ((u or true) equals not (v and false));
```
