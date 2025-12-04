export function isValidLetter(letter: string): boolean {
  return letter.trim().length > 0 && /^[A-Za-z]$/.test(letter);
}
