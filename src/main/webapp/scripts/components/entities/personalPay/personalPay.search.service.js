'use strict';

angular.module('stepApp')
    .factory('PersonalPaySearch', function ($resource) {
        return $resource('api/_search/personalPays/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
