'use strict';

angular.module('stepApp')
    .factory('HrDesignationSetupSearch', function ($resource) {
        return $resource('api/_search/hrDesignationSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
