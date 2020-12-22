class App {
    /**
     * elements
     */
    getHeadings(level: number) {
        return $(`h${level}`)
    }

    /**
     * methods
     */
    open(path = '/') {
        browser.url(path)
    }
}

export default new App()
