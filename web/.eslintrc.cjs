module.exports = {
    "env": {
        "browser": true,
        "es2021": true
    },
    "extends": [
        "eslint:recommended",
        "plugin:vue/vue3-essential",
        "plugin:@typescript-eslint/recommended"
    ],
    "overrides": [
    ],
    "parser": "vue-eslint-parser",
    "parserOptions": {
        "ecmaVersion": "latest",
        "sourceType": "module",
        "parser": "@typescript-eslint/parser"
    },
    "plugins": [
        "vue",
        "@typescript-eslint"
    ],
    "rules": {
        "vue/multi-word-component-names": ["error", {
                "ignores": ["index"]
            }
        ],
        "no-inline-comments": "error",
        "max-lines-per-function": [
            "error"
        ],
        "max-len": [
            "error",
            {
                "code": 80
            }
        ],
        "comma-spacing": [
            "error",
            {
                "before": false,
                "after": true
            }
        ],
        "no-label-var": "error",
        "no-var": "error",
        "arrow-spacing": "error",
        "arrow-parens": [
            "error",
            "always"
        ],
        "lines-around-comment": [
            "error",
            {
                "beforeBlockComment": true
            }
        ],
        "comma-style": [
            "error",
            "last"
        ],
        "comma-dangle": [
            "error",
            "never"
        ],
        "brace-style": [
            "error",
            "stroustrup"
        ],
        "array-element-newline": [
            "error",
            "never"
        ],
        "array-bracket-spacing": [
            "error",
            "never"
        ],
        "array-bracket-newline": [
            "error",
            "always"
        ],
        "init-declarations": [
            "error",
            "always"
        ],
        "no-multi-str": "error",
        "no-multi-spaces": "error",
        "no-extra-label": "error",
        "no-eq-null": "error",
        "no-empty-function": "error",
        "no-else-return": "error",
        "no-alert": "error",
        "dot-notation": "error",
        "dot-location": [
            "error",
            "property"
        ],
        "curly": [
            "error",
            "all"
        ],
        "linebreak-style": [
            "error",
            "windows"
        ],
        "semi": [
            "error",
            "always"
        ],
        "no-unused-vars": [
            "error",
            { "argsIgnorePattern": "^_" }
        ]
    }
}