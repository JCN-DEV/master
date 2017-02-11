'use strict';

describe('InstituteInfraInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstituteInfraInfo, MockInstitute, MockInstBuilding, MockInstLand;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstituteInfraInfo = jasmine.createSpy('MockInstituteInfraInfo');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockInstBuilding = jasmine.createSpy('MockInstBuilding');
        MockInstLand = jasmine.createSpy('MockInstLand');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstituteInfraInfo': MockInstituteInfraInfo,
            'Institute': MockInstitute,
            'InstBuilding': MockInstBuilding,
            'InstLand': MockInstLand
        };
        createController = function() {
            $injector.get('$controller')("InstituteInfraInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instituteInfraInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
