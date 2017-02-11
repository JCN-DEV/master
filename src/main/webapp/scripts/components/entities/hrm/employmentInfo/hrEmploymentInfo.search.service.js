'use strict';

angular.module('stepApp')
    .factory('HrEmploymentInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmploymentInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
