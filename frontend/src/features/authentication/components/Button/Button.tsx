import type { ButtonHTMLAttributes } from "react"


type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & { outline?: boolean }

export const Button = ({ outline,children, ...rest }: ButtonProps) => {
    return (
        <button
            {...rest}
            className={`${outline ? "flex items-center justify-center gap-2 w-full p-4 bg-white text-black border border-black/60 disabled:border-black rounded-[9999px] my-4 font-bold transition-0.3s disabled:text-black disabled:bg-[#D9D9D9] disables:cursor-not-allowed hover:bg-gray-100" : "flex items-center justify-center gap-2 w-full p-4 bg-violet-400 text-white border-none rounded-[9999px] my-4 font-bold transition-0.3s disabled:text-black disabled:bg-[#D9D9D9] disables:cursor-not-allowed hover:bg-violet-500"}`}
        >
           {children}
        </button>
    )
}
