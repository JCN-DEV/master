'use strict';

angular.module('stepApp')
    .factory('TrainingSummarySearch', function ($resource) {
        return $resource('api/_search/trainingSummarys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
