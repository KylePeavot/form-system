const colors = require('tailwindcss/colors')

module.exports = {
    purge: [
        './src/**/*.html',
        './src/**/*.vue',
        './src/**/*.jsx',
    ],
    theme: {
        colors: {
            gray: colors.coolGray,
            blue: {
                50: '#f0f9ff',
                100: '#e0f2fe',
                200: '#bae6fd',
                300: '#7dd3fc',
                400: '#38bdf8',
                500: '#0e528d',
                600: '#084070',
                700: '#05345c', // Kent blue
                800: '#022541', // Kent nav page selection
                900: '#032234',
            },
            red: colors.rose,
            pink: colors.fuchsia,
            white: '#fff',
            green: colors.green
        },
        minHeight: {
            '0': '0',
            '1/4': '25%',
            '1/2': '50%',
            '3/4': '75%',
            'full': '100%',
            'screen': '100vh',
            'screen-half': '50vh'
        }
    },
    plugins: [
        require("@tailwindcss/forms")
    ]
}