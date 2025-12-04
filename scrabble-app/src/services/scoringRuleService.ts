import { get } from "../utils/api";

export async function getPoint(letter: string) {
  try {
    const res = await get(`/scoring-rules/letter/${letter}`);
    return res.data.point;
  } catch (error) {
    console.error("Error fetching point for letter:", letter, error);
    throw error;
  }
}
