'use strict';

angular.module('stepApp')
    .factory('AlmShiftSetupSearch', function ($resource) {
        return $resource('api/_search/almShiftSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
