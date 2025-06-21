import type { ReactNode } from "react";

interface BoxProps {
  children: ReactNode;
}

export const Box = ({ children }: BoxProps) => {
  return (
    <div className="md:p-6 md:w-[30rem] my-0 mx-auto bg-white rounded-lg shadow-[0_4px_12px_rgba(0,0,0,0.5)]">
      {children}
    </div>
  )
}
