import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FilmSelectionService {

  STORAGE_KEY: string = 'sakila-film-selection-film-ids';

  constructor() { }

  addFilmToSelection(filmId: number) {
    let selectedItems: Array<number> = this.getSelectedItems();
    if (selectedItems === null) selectedItems = new Array();

    if (selectedItems.indexOf(filmId) == -1) {
      selectedItems.push(filmId);
      this.setSelectedItems(selectedItems);
    }
  }

  removeFilmFromSelection(filmId: number) {
    let selectedItems: Array<number> = this.getSelectedItems();
    if (selectedItems === null) return

    let elementIndex: number = selectedItems.indexOf(filmId);
    if (elementIndex > -1) {
      selectedItems = selectedItems.splice(elementIndex, 1);
      this.setSelectedItems(selectedItems);
    }
  }

  getSelectedFilmIds(): Array<number> {
    return this.getSelectedItems();
  }

  private getSelectedItems(): Array<number> {
    return JSON.parse(sessionStorage.getItem(this.STORAGE_KEY));
  }

  private setSelectedItems(selectedItems: Array<number>) {
    sessionStorage.setItem(this.STORAGE_KEY, JSON.stringify(selectedItems));
  }
}
