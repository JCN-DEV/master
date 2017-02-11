'use strict';

angular.module('stepApp')
    .factory('AlmEarningFrequencySearch', function ($resource) {
        return $resource('api/_search/almEarningFrequencys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
