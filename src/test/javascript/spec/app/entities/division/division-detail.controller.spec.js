'use strict';

describe('Division Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDivision, MockDistrict, MockCountry;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDivision = jasmine.createSpy('MockDivision');
        MockDistrict = jasmine.createSpy('MockDistrict');
        MockCountry = jasmine.createSpy('MockCountry');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Division': MockDivision,
            'District': MockDistrict,
            'Country': MockCountry
        };
        createController = function() {
            $injector.get('$controller')("DivisionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:divisionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
