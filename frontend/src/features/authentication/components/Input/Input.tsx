import type { InputHTMLAttributes } from "react"

type InputProps = InputHTMLAttributes<HTMLInputElement> & { label: string }

export const Input = ({ label, ...rest }: InputProps) => {
    return (
        <div className="grid gap-2 my-2">
            <label>{label}</label>
            <input
                {...rest}
                className="w-full p-2 border border-black/60 rounded-[4px] focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
        </div>
    )
}
