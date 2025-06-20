import { createBrowserRouter, RouterProvider } from "react-router"
import { Feed } from "./features/Feed/pages/Feed"
import { Login } from "./features/authentication/pages/Login/Login"
import { Signup } from "./features/authentication/pages/Signup/Signup"
import { ResetPassword } from "./features/authentication/pages/ResetPassword/ResetPassword"
import { VerifyEmail } from "./features/authentication/pages/VerifyEmail/VerifyEmail"
import { Layout } from "./features/authentication/components/Layout/Layout"

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        path: "/",
        element: <Feed />,
      },
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/signup",
        element: <Signup />,
      },
      {
        path: "/request-password-reset",
        element: <ResetPassword />,
      },
      {
        path: "/verify-email",
        element: <VerifyEmail />,
      },
    ]
  }
])



export const App = () => {
  return (
    <RouterProvider router={router} />
  )
}
