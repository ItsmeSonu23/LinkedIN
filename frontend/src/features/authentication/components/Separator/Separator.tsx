import type { ReactNode } from "react"

export const Separator = ({children}:{children:ReactNode}) => {
  return (
    <div className="grid grid-cols-[1fr_auto_1fr] gap-4 my-4 items-center after:content-[''] after:h-[1px] after:bg-black/30 after:my-4 after:mx-0 before:content-[''] before:h-[1px] before:bg-black/30 before:my-4 before:mx-0">
      {children}
    </div>
  )
}
