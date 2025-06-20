import type { ReactNode } from "react";

interface BoxProps {
  children: ReactNode;
}

export const Box = ({ children }: BoxProps) => {
  return (
    <div>
      {children}
    </div>
  )
}
