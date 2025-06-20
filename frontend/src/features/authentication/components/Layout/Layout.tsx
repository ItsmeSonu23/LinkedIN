import { Outlet } from "react-router"
import { Footer } from "./Footer"
import { Header } from "./Header"

export const Layout = () => {
    return (
        <div className="grid grid-rows-[auto,1fr,auto] min-h-screen">

            <Header />
            <main className="max-w-[74rem] w-full mxy-0 mx-auto p-4">
                <Outlet />
            </main>
            <Footer />
        </div>
    )
}
