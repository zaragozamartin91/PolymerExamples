# paper-typeahead-input

[![Travis](https://img.shields.io/travis/cheonhyangzhang/paper-typeahead-input.svg?style=flat)](https://travis-ci.org/cheonhyangzhang/paper-typeahead-input)

[![Coverage Status](https://coveralls.io/repos/github/cheonhyangzhang/paper-typeahead-input/badge.svg?branch=master)](https://coveralls.io/github/cheonhyangzhang/paper-typeahead-input?branch=master)

An element providing a starting point for your own reusable Polymer elements.

## Demo


[Documentation and Demo](http://cheonhyangzhang.github.io/paper-typeahead-input/components/paper-typeahead-input/)

## Installation

	bower install paper-typeahead-input

## Dependencies

Element dependencies are managed via [Bower](http://bower.io/). You can
install that via:

    npm install -g bower

Then, go ahead and download the element's dependencies:

    bower install


## Playing With Your Element

If you wish to work on your element in isolation, we recommend that you use
[Polyserve](https://github.com/PolymerLabs/polyserve) to keep your element's
bower dependencies in line. You can install it via:

    npm install -g polyserve

And you can run it via:

    polyserve

Once running, you can preview your element at
`http://localhost:8080/components/paper-typeahead-input/`, where `paper-typeahead-input` is the name of the directory containing it.

## Want to contribute?
Please read the [CONTRIBUTING.md](https://github.com/cheonhyangzhang/paper-typeahead-input/blob/master/CONTRIBUTING.md) before making your changes.

## Testing Your Element

Simply navigate to the `/test` directory of your element to run its tests. If
you are using Polyserve: `http://localhost:8080/components/paper-typeahead-input/test/`

### web-component-tester

The tests are compatible with [web-component-tester](https://github.com/Polymer/web-component-tester).
Install it via:

    npm install -g web-component-tester

Then, you can run your tests on _all_ of your local browsers via:

    wct

#### WCT Tips

`wct -l chrome` will only run tests in chrome.

`wct -p` will keep the browsers alive after test runs (refresh to re-run).

`wct test/some-file.html` will test only the files you specify.
