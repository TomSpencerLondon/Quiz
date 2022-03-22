const defaultTheme = require('tailwindcss/defaultTheme');
const plugin = require('tailwindcss/plugin');
module.exports = {
    purge: [
        './src/main/resources/templates/**/*.html'
    ],
    darkMode: false, // or 'media' or 'class'
    theme: {
        extend: {
            fontFamily: {
                sans: ['Inter var', ...defaultTheme.fontFamily.sans],
            },
        }
    },
    variants: {
        extend: {},
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
    ]
};