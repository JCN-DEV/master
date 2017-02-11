'use strict';

angular.module('stepApp')
    .factory('CountrySearch', function ($resource) {
        return $resource('api/_search/countrys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('CountrysByName', function ($resource) {
        return $resource('api/countrys/byName', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
