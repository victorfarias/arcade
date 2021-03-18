#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import argparse

def keep(line: str, store: bool):
    if store:
        return True

    for token in ["import", "    @", "        ", "    }"]:
        if line.startswith(token):
            return False
    return True

def transform(line: str, store: bool):
    if store:
        return line
    return line.replace("){", ") {").replace(") {", ");")

def process(content: str) -> str:
    lines = content.split("\n")
    output = []
    store = False
    for line in lines:
        if line == "//STORE_ON":
            store = True
        elif line == "//STORE_OFF":
            store = False
        elif keep(line, store):
            line = transform(line, store)
            output.append(line)
    return "\n".join(output)

if __name__ == '__main__':
    parse = argparse.ArgumentParser()
    parse.add_argument("input", type=str)
    args = parse.parse_args()
    with open(args.input) as f:
        print(process(f.read()))