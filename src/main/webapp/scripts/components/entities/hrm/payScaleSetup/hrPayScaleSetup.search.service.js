'use strict';

angular.module('stepApp')
    .factory('HrPayScaleSetupSearch', function ($resource) {
        return $resource('api/_search/hrPayScaleSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
