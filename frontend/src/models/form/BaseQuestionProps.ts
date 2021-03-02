export default class BaseQuestionProps {
  private _level: number;
  private _title: string;
  private _guidance: string;

  constructor(level: number, title: string, guidance: string) {
    this._level = level;
    this._title = title;
    this._guidance = guidance;
  }

  get level(): number {
    return this._level;
  }

  set level(value: number) {
    this._level = value;
  }

  get title(): string {
    return this._title;
  }

  set title(value: string) {
    this._title = value;
  }

  get guidance(): string {
    return this._guidance;
  }

  set guidance(value: string) {
    this._guidance = value;
  }
}