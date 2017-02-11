'use strict';

angular.module('stepApp')
    .factory('JpLanguageProficiencySearch', function ($resource) {
        return $resource('api/_search/jpLanguageProficiencys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JpLanguageProfeciencyJpEmployee', function ($resource) {
        return $resource('api/languageProficencies/jpEmployee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
