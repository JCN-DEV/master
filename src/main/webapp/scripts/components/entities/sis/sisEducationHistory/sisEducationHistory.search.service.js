'use strict';

angular.module('stepApp')
    .factory('SisEducationHistorySearch', function ($resource) {
        return $resource('api/_search/sisEducationHistorys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
