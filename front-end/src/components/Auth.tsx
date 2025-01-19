import { Navigate, Outlet } from "react-router-dom";

export default function Auth() {
    return (
        <div className="body">
            <div className="container">
                <Outlet />
                {/* <Navigate to="/auth/login" replace /> */}
            </div>
        </div>
    )
}