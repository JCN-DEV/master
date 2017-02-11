/* globals $ */
'use strict';

angular.module('stepApp')
    .directive('stepAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });
