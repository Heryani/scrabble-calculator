import axios from "axios";

const api = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

export async function get(url: string) {
  return await api.get(url);
}

export async function post(url: string, data: unknown) {
  return await api.post(url, data);
}
