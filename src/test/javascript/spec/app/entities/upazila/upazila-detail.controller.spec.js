'use strict';

describe('Upazila Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockUpazila, MockDistrict;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockUpazila = jasmine.createSpy('MockUpazila');
        MockDistrict = jasmine.createSpy('MockDistrict');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Upazila': MockUpazila,
            'District': MockDistrict
        };
        createController = function() {
            $injector.get('$controller')("UpazilaDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:upazilaUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
