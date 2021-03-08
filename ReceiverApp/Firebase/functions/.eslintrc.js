module.exports = {
  root: true,
  env: {
    es6: true,
    node: true,
  },
  extends: [
    "eslint:recommended",
    "google",
  ],
  rules: {
    quotes: ["error", "double"],
    indent: [1, "tab"],
    "no-tabs": 0,
    "quote-props": [2, "as-needed"],
    "linebreak-style": 0,
  },
};
