module.exports = {
    module: {
        rules: [
            {
                test: /\.s?css$/i,
                use: ['style-loader', 'css-loader', 'postcss-loader'],
            },
        ],
    },
};