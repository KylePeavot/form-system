const colors = require('tailwindcss/colors')

module.exports = {
    theme: {
        colors: {
            gray: colors.coolGray,
            blue: {
                50: '#f0f9ff',
                100: '#e0f2fe',
                200: '#bae6fd',
                300: '#7dd3fc',
                400: '#38bdf8',
                500: '#0ea5e9',
                600: '#0284c7',
                700: '#05345c', // Kent blue
                800: '#032745', // Kent nav page selection
                900: '#0c4a6e',
            },
            red: colors.rose,
            pink: colors.fuchsia,
            white: '#fff',
        }
    }
}