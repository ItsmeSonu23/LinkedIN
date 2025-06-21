import type { ButtonHTMLAttributes } from "react"


type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & { outline?: boolean }

export const Button = ({ outline,children, ...rest }: ButtonProps) => {
    return (
        <button
            {...rest}
            className={`flex items-center justify-center gap-2 w-full p-4 bg-violet-400 text-white border-none rounded-[9999px] my-4 font-bold transition-0.3s disabled:text-black disabled:bg-[#D9D9D9] disables:cursor-not-allowed hover:bg-violet-500 ${outline ? "bg-white text-black border border-black/60 p-2 font-normal disabled:border-black" : ""}`}
        >
           {children}
        </button>
    )
}
