import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FilmSelectionService {

  STORAGE_KEY: string = 'sakila-film-selection-film-ids';

  constructor() { }

  addFilmToSelection(filmId: string) {
    let selectedItems: Array<string> = this.getSelectedItems();
    if (selectedItems == null) selectedItems = new Array();

    if (selectedItems.indexOf(filmId) == -1) {
      selectedItems.push(filmId);
      this.setSelectedItems(selectedItems);
    }
  }

  removeFilmFromSelection(filmId: string) {
    let selectedItems: Array<string> = this.getSelectedItems();
    if (selectedItems == null) return

    let elementIndex: number = selectedItems.indexOf(filmId);
    if (elementIndex > -1) {
      selectedItems.splice(elementIndex, 1);
      this.setSelectedItems(selectedItems);
    }
  }

  getSelectedFilmIds(): Array<string> {
    return this.getSelectedItems();
  }

  private getSelectedItems(): Array<string> {
    return JSON.parse(sessionStorage.getItem(this.STORAGE_KEY));
  }

  private setSelectedItems(selectedItems: Array<string>) {
    sessionStorage.setItem(this.STORAGE_KEY, JSON.stringify(selectedItems));
  }
}
