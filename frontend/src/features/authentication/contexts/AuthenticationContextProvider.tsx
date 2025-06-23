import { createContext, useContext, useEffect, useState } from "react"
import { Navigate, Outlet, useLocation } from "react-router";
import Loader from "../../../components/Loader/Loader";
import axios from "axios";

interface User {
    id: string;
    email: string;
    emailVerified: boolean;
}

export interface AuthenticationContextType {
    user?: User | null;
    login: (email: string, password: string) => Promise<void>;
    signup: (email: string, password: string) => Promise<void>;
    logout: () => Promise<void>;
}

const AuthenticationContext = createContext<AuthenticationContextType | null>(null);

export const useAuthentication = (): AuthenticationContextType => {
    return useContext(AuthenticationContext) as AuthenticationContextType;
}

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    headers: {
        "Content-Type": "application/json"
    }
})

export const AuthenticationContextProvider = () => {
    const [user, setUser] = useState<User | null>(null);
    const [isLoading, setIsLoading] = useState(true);
    const location = useLocation();
    const isOnAuthPage =
        location.pathname === "/login" ||
        location.pathname === "/signup" ||
        location.pathname === "/request-password-reset";

    const login = async (email: string, password: string) => {
        try {
            const response = await api.post("/api/v1/authentication/login", { email, password });
            const { token } = response.data;
            localStorage.setItem("token", token);
            await fetchUser(); // refresh user after login
        } catch (error: any) {
            throw new Error(error.response?.data?.message || "Login failed");
        }
    };

    const signup = async (email: string, password: string) => {
        try {
            const response = await api.post("/api/v1/authentication/register", { email, password });
            const { token } = response.data;
            localStorage.setItem("token", token);
            await fetchUser();
        } catch (error: any) {
            throw new Error(error.response?.data?.message || "Signup failed");
        }
    }

    const logout = async () => {
        localStorage.removeItem("token");
        setUser(null);
    }

    const fetchUser = async () => {
        const token = localStorage.getItem("token");
        if (!token) {
            setIsLoading(false);
            return;
        }

        try {
            const response = await api.get("/api/v1/authentication/user", {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setUser(response.data);
        } catch (error) {
            console.error(error);
            setUser(null);
        } finally {
            setIsLoading(false);
        }
    }



    useEffect(() => {
        if (!user) {
            fetchUser();
        }
    }, [location.pathname])

    if (isLoading) {
        return <Loader />
    }

    if (!isLoading && !user && !isOnAuthPage) {
        return <Navigate to="/login" />
    }

    if (user && user?.emailVerified && isOnAuthPage) {
        return <Navigate to="/" />
    }

    return (
        <AuthenticationContext.Provider value={{ user, login, signup, logout }}>
            {user && !user.emailVerified ? <Navigate to="/verify-email" /> : null}
            <Outlet />
        </AuthenticationContext.Provider>
    )
}


