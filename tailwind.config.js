const defaultTheme = require('tailwindcss/defaultTheme');
const plugin = require('tailwindcss/plugin');
module.exports = {
    content: ['./src/main/resources/templates/**/*.html'],
    theme: {
        extend: {
            fontFamily: {
                sans: ['Inter var', ...defaultTheme.fontFamily.sans],
            },
        },
    },
    plugins: [
        require('@tailwindcss/forms'),
        require('@tailwindcss/typography'),
        plugin(function ({addBase, theme}) {
            addBase({
                'h1': {fontSize: theme('fontSize.2xl')},
                'h2': {fontSize: theme('fontSize.xl')},
                'h3': {fontSize: theme('fontSize.lg')},
                'ol,ul': {listStyleType: 'revert'}
            })
        })
    ],
};