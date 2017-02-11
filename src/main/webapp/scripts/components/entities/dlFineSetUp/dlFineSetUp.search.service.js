'use strict';

angular.module('stepApp')
    .factory('DlFineSetUpSearch', function ($resource) {
        return $resource('api/_search/dlFineSetUps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
