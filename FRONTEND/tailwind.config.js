/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,js,ts}'],
  theme: {
    extend: {},
  },
  daisyui: {
    themes: ["light", "dark", "lofi"],
  },
  plugins: [require("@tailwindcss/typography"), require("daisyui")],
}

