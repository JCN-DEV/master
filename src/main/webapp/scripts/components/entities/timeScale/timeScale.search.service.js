'use strict';

angular.module('stepApp')
    .factory('TimeScaleSearch', function ($resource) {
        return $resource('api/_search/timeScales/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
